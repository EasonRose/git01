package com.shsxt.xmjf.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.xmjf.api.constants.ItemStatus;
import com.shsxt.xmjf.api.constants.XmjfConstant;
import com.shsxt.xmjf.api.po.*;
import com.shsxt.xmjf.api.querys.BusItemInvestQuery;
import com.shsxt.xmjf.api.service.IBasUserSecurityService;
import com.shsxt.xmjf.api.service.IBusItemInvestService;
import com.shsxt.xmjf.api.service.ISmsService;
import com.shsxt.xmjf.api.service.IUserService;
import com.shsxt.xmjf.api.utils.AssertUtil;
import com.shsxt.xmjf.api.utils.MD5;
import com.shsxt.xmjf.server.db.dao.*;
import com.shsxt.xmjf.server.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BusItemInvestServiceImpl implements IBusItemInvestService {
    @Resource
    private BusItemInvestMapper busItemInvestMapper;

    @Resource
    private BasItemMapper basItemMapper;

    @Resource
    private IBasUserSecurityService basUserSecurityService;

    @Resource
    private BusAccountMapper busAccountMapper;


    @Resource
    private BasExperiencedGoldMapper basExperiencedGoldMapper;

    @Resource
    private BusUserStatMapper busUserStatMapper;

    @Resource
    private BusAccountLogMapper busAccountLogMapper;


    @Resource
    private BusIncomeStatMapper busIncomeStatMapper;

    @Resource
    private BusIntegralLogMapper busIntegralLogMapper;

    @Resource
    private BusUserIntegralMapper busUserIntegralMapper;

    @Resource
    private ISmsService smsService;

    @Resource
    private IUserService userService;

    @Override
    public PageInfo<Map<String, Object>> queryInvestsByParams(BusItemInvestQuery busItemInvestQuery) {
        PageHelper.startPage(busItemInvestQuery.getPageNum(), busItemInvestQuery.getPageSize());
        List<Map<String, Object>> vals = busItemInvestMapper.queryInvestsByParams(busItemInvestQuery);
        if (!CollectionUtils.isEmpty(vals)) {
            for(Map<String,Object> map:vals) {
               String phone= (String) map.get("phone");
               map.put("phone",phone.substring(0,3)+ StringUtil.getReplaceStr(phone.substring(3,7))+phone.substring(7));
            }
        }
        return new PageInfo<Map<String, Object>>(vals);
    }

    @Override
    public void addBusItemInvest(Integer itemId, Integer userId, String busiPwd,BigDecimal amount, Integer isUseExpGold) {
        /**
         * 1.参数基本校验
         *      itemId:非空  记录必须存在 开放状态  有效期内
         *      userId:是否实名
         *      amount:非空  >0
         *      busiPwd:非空  密码一致
         * 2.业务场景参数合法性
         *     itemId:
         *        pc端项目  移动端 定向标 不能参与投资
         *        新手标项目仅限首投
         *        项目剩余投资金额<最低投资 项目不可投资
         *     amount:
         *        大于等于项目最低投资(最低投资存在时)
         *        小于等于最高投资(最高投资存在时)
         *        小于等于账户可用金额
         *    isUseExpGold:
         *        使用体验金
         *            判断体验金 是否使用
         *              使用:只能使用一次
         *              未使用:判断体验金有效期
         * 3.表记录更新
         *       bas_item	项目表
         *           进行中金额  投资次数  进度 状态
                 bus_item_invest	项目投资表
                      添加投资记录   利息计算
                 bus_user_stat	用户统计表
                       投资字段更新
                 bus_account	用户账户表
                        总金额  可用 可提现  冻结 代收本金
                 bus_account_log	用户账户操作日志表
                        账户资金变化日志记录
                 bus_income_stat	用户收益信息表
                        总收益  已赚收益
                 bus_user_integral	用户积分表
                          总积分  可用积分
                 bus_integral_log	积分操作日志表
                         积分变动日志记录
         4.通知短信
         */

        AssertUtil.isTrue(null==itemId,"投资记录项目id不能为空!");
        BasItem basItem=basItemMapper.queryById(itemId);
        AssertUtil.isTrue(null==basItem,"待投资的记录不存在!");
        AssertUtil.isTrue(!(basItem.getItemStatus()== ItemStatus.OPEN),"项目为非开放状态，不能参与投资!");
        AssertUtil.isTrue(basItem.getEndTime().getTime()-System.currentTimeMillis()<0,"项目投资期限已结束!");
        basUserSecurityService.checkUserIsRealName(userId);
        AssertUtil.isTrue(!(basUserSecurityService.queryBasUserSecurityByUserId(userId).getPaymentPassword().equals(MD5.toMD5(busiPwd))),"交易密码错误!");
        AssertUtil.isTrue(null==amount,"请输入投资金额!");
        AssertUtil.isTrue(amount.compareTo(BigDecimal.ZERO)<=0,"投资金额必须大于0");
        AssertUtil.isTrue(basItem.getMoveVip()==1,"移动端项目,PC端暂不支持投资!");
        AssertUtil.isTrue(StringUtils.isNotBlank(basItem.getPassword()),"定向标普通用户暂不支持投资!");
        if(basItem.getItemIsnew()==1){
            AssertUtil.isTrue(busItemInvestMapper.queryIsInvestNewItemByUserId(userId)>0,"新手标项目仅限首投!");
        }
        BusAccount busAccount=busAccountMapper.queryBusAccountByUserId(userId);
        AssertUtil.isTrue(amount.compareTo(busAccount.getUsable())==1,"投资金额不能大于账户可用金额!");
        BigDecimal  singleMinInvestment= basItem.getItemSingleMinInvestment();
        BigDecimal syAmount=basItem.getItemAccount().subtract(basItem.getItemOngoingAccount());
        if(singleMinInvestment.compareTo(BigDecimal.ZERO)==1){
            AssertUtil.isTrue(singleMinInvestment.compareTo(syAmount)==1,"项目即将进行截标，不支持投资!");
            AssertUtil.isTrue(amount.compareTo(singleMinInvestment)==-1,"投资金额不能小于最低投资");
        }
        AssertUtil.isTrue(amount.compareTo(syAmount)==1,"投资金额不能大于项目剩余金额!");

        BigDecimal singleMaxInvestment=basItem.getItemSingleMaxInvestment();
        if(singleMaxInvestment.compareTo(BigDecimal.ZERO)==1){
            AssertUtil.isTrue(amount.compareTo(singleMaxInvestment)==1,"投资金额不能大于最大投资!");
        }

        BigDecimal oldAmount=amount;
        if(isUseExpGold==1){
            // 使用体验金
            BasExperiencedGold basExperiencedGold=basExperiencedGoldMapper.queryBasExperiencedGoldByUserId(userId);
            AssertUtil.isTrue(basExperiencedGold.getStatus()==1,"体验金已使用!");
            AssertUtil.isTrue(basExperiencedGold.getStatus()==3,"体验金已过期!");
            AssertUtil.isTrue(basExperiencedGold.getStatus()==4,"体验金处于使用中!");
            amount=amount.add(basExperiencedGold.getAmount());
            basExperiencedGold.setStatus(1);
            AssertUtil.isTrue(basExperiencedGoldMapper.update(basExperiencedGold)<1,XmjfConstant.OPS_FAILED_MSG);
        }

        // bas_item
        basItem.setInvestTimes(basItem.getInvestTimes()+1);

        // 添加投资的本金(体验金不在计算范围内)
        basItem.setItemOngoingAccount(basItem.getItemOngoingAccount().add(oldAmount));
        if(basItem.getItemOngoingAccount().compareTo(basItem.getItemAccount())==0){
            // 满标
            basItem.setItemStatus(ItemStatus.FULL_COMPLETE);
            basItem.setItemScale(BigDecimal.valueOf(100));
        }else{
            basItem.setItemScale(basItem.getItemOngoingAccount().divide(basItem.getItemAccount(), MathContext.DECIMAL32).divide(BigDecimal.valueOf(1), 4, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
        }
        basItem.setUpdateTime(new Date());
        AssertUtil.isTrue(basItemMapper.update(basItem)<1, XmjfConstant.OPS_FAILED_MSG);

        // bus_user_stat
        BusUserStat busUserStat= busUserStatMapper.queryBusUserStatByUserId(userId);
        busUserStat.setInvestCount(busUserStat.getInvestCount()+1);
        busUserStat.setInvestAmount(busUserStat.getInvestAmount().add(oldAmount));// 加本金
        AssertUtil.isTrue(busUserStatMapper.update(busUserStat)<1,XmjfConstant.OPS_FAILED_MSG);

        // bus_item_invest
        BusItemInvest busItemInvest=new BusItemInvest();
        busItemInvest.setActualCollectAmount(BigDecimal.ZERO);
        busItemInvest.setActualCollectInterest(BigDecimal.ZERO);
        busItemInvest.setActualCollectPrincipal(BigDecimal.ZERO);
        BigDecimal lx= amount.multiply(basItem.getItemRate().add(basItem.getItemAddRate()).divide(BigDecimal.valueOf(100))).multiply(BigDecimal.valueOf(basItem.getItemCycle()).divide(BigDecimal.valueOf(365), MathContext.DECIMAL32)).divide(BigDecimal.valueOf(1), 2, BigDecimal.ROUND_HALF_EVEN);
        busItemInvest.setActualUncollectAmount(oldAmount.add(lx));
        busItemInvest.setActualUncollectInterest(lx);
        busItemInvest.setActualUncollectPrincipal(oldAmount);
        busItemInvest.setAddtime(new Date());
        busItemInvest.setCollectInterest(lx);
        busItemInvest.setCollectAmount(oldAmount.add(lx));
        busItemInvest.setCollectPrincipal(oldAmount);
        busItemInvest.setInvestAmount(oldAmount);
        busItemInvest.setInvestCurrent(1);// 定期
        busItemInvest.setInvestDealAmount(oldAmount);
        /**
         * UUID
         * 加密算法
         * 雪花算法
         */
        String investOrder="XMJF_TZ_"+new SimpleDateFormat("YYYYMMddHHmmssS").format(new Date());
        busItemInvest.setInvestOrder(investOrder);
        busItemInvest.setInvestStatus(0);
        busItemInvest.setInvestType(1);
        busItemInvest.setItemId(itemId);
        busItemInvest.setUpdatetime(new Date());
        busItemInvest.setUserId(userId);
        AssertUtil.isTrue(busItemInvestMapper.insert(busItemInvest)<1,XmjfConstant.OPS_FAILED_MSG);

        // bus_account
        busAccount.setUsable(busAccount.getUsable().subtract(oldAmount));
        busAccount.setCash(busAccount.getCash().subtract(oldAmount));
        busAccount.setTotal(busAccount.getTotal().add(lx));
        busAccount.setFrozen(busAccount.getFrozen().add(oldAmount));
        busAccount.setWait(busAccount.getWait().add(oldAmount));
        AssertUtil.isTrue(busAccountMapper.update(busAccount)<1,XmjfConstant.OPS_FAILED_MSG);


        BusAccountLog busAccountLog=new BusAccountLog();
        busAccountLog.setWait(busAccount.getWait());
        busAccountLog.setFrozen(busAccount.getFrozen());
        busAccountLog.setTotal(busAccount.getTotal());
        busAccountLog.setCash(busAccount.getCash());
        busAccountLog.setUsable(busAccount.getUsable());
        busAccountLog.setAddtime(new Date());
        busAccountLog.setOperMoney(oldAmount);
        busAccountLog.setRemark("PC端用户投资");
        busAccountLog.setRepay(busAccount.getRepay());
        busAccountLog.setBudgetType(2);// 支出
        busAccountLog.setOperType("PC端用户投资");
        busAccountLog.setUserId(userId);
        AssertUtil.isTrue(busAccountLogMapper.insert(busAccountLog)<1,XmjfConstant.OPS_FAILED_MSG);


        // bus_income_stat
        BusIncomeStat busIncomeStat=busIncomeStatMapper.queryBusIncomeStatByUserId(userId);
        busIncomeStat.setWaitIncome(busIncomeStat.getWaitIncome().add(lx));
        busIncomeStat.setTotalIncome(busIncomeStat.getTotalIncome().add(lx));
        AssertUtil.isTrue(busIncomeStatMapper.update(busIncomeStat)<1,XmjfConstant.OPS_FAILED_MSG);


        // bus_user_integral
        BusUserIntegral busUserIntegral=busUserIntegralMapper.queryBusUserIntegralByUserId(userId);
        busUserIntegral.setTotal(busUserIntegral.getTotal()+200);// 赠送200积分
        busUserIntegral.setUsable(busUserIntegral.getUsable()+200);
        AssertUtil.isTrue(busUserIntegralMapper.update(busUserIntegral)<1,XmjfConstant.PAY_FAILED_MSG);


        // bus_integral_log
        BusIntegralLog busIntegralLog=new BusIntegralLog();
        busIntegralLog.setWay("用户投资");
        busIntegralLog.setUserId(userId);
        busIntegralLog.setStatus(0);// 收入
        busIntegralLog.setIntegral(200);
        busIntegralLog.setAddtime(new Date());
        AssertUtil.isTrue(busIntegralLogMapper.insert(busIntegralLog)<1,XmjfConstant.PAY_FAILED_MSG);
        User user=userService.queryUserByUserId(userId);
        smsService.sendSms(user.getMobile(),XmjfConstant.SMS_INVEST_SUCCESS_NOTIFY_TYPE);
    }


    public static void main(String[] args) {
        /*BigDecimal a=BigDecimal.valueOf(300);
        BigDecimal b=BigDecimal.valueOf(2100);
        System.out.println(a.divide(b, MathContext.DECIMAL32).divide(BigDecimal.valueOf(1), 4, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));*/
        /**
         * 单利计算
         *    本金     年化率    期限(天)
         *    10000      0.03    365       =300
         *      10000*0.03*(183/365)     150
         */
       /* BigDecimal amount=BigDecimal.valueOf(500);
        BigDecimal scale=BigDecimal.valueOf(0.03);
        BigDecimal total=BigDecimal.valueOf(60);
        System.out.println(amount.multiply(scale).multiply(total.divide(BigDecimal.valueOf(365), MathContext.DECIMAL32)).divide(BigDecimal.valueOf(1), 2, BigDecimal.ROUND_HALF_EVEN));*/
        BigDecimal lx= BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(10).divide(BigDecimal.valueOf(100))).multiply(BigDecimal.valueOf(30).divide(BigDecimal.valueOf(365), MathContext.DECIMAL32)).divide(BigDecimal.valueOf(1), 2, BigDecimal.ROUND_HALF_EVEN);
        System.out.println(lx);

    }
}
