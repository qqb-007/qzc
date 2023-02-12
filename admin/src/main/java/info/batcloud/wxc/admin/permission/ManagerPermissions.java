package info.batcloud.wxc.admin.permission;

import info.batcloud.wxc.core.settings.*;

import java.util.ArrayList;
import java.util.List;

public enum ManagerPermissions implements PermissionItem {

    NONE("无权限", null),
    OPERATION_CENTER("运营中心", null),
    STORE_USER_FOOD("门店商品管理", OPERATION_CENTER),
    STORE_USER_FOOD_BATCH_VERIFY("门店商品批量审核", STORE_USER_FOOD),
    STORE_USER_FOOD_BATCH_DELETE("门店商品批量删除", STORE_USER_FOOD),
    STORE_FOOD_CHECK("门店商品校对", OPERATION_CENTER),
    ORDER_CENTER("订单中心", OPERATION_CENTER),
    ORDER_MANAGE("订单管理", ORDER_CENTER),
    ORDER_SYNC_LIST("订单通知与同步", ORDER_CENTER),
    ORDER_EXPORT("订单导出", ORDER_CENTER),
    STORE_FOOD_APPLICATION_MANAGE("商品申请管理", OPERATION_CENTER),

    BASE_MANAGE("基础管理", null),
    FOOD_MANAGE("商品管理", BASE_MANAGE),
    FOOD_LIST("商品列表", FOOD_MANAGE),
    FOOD_CATEGORY_MANAGE("分类管理", FOOD_MANAGE),
    STORE_MANAGE("门店管理", BASE_MANAGE),
    STORE_LIST("门店列表", STORE_MANAGE),
    STORE_USER_MANAGE("商家", STORE_MANAGE),
    FOOD_SUPPLIER_MANAGE("供应商管理", STORE_MANAGE),
    ORG_MANAGE("组织架构", BASE_MANAGE),
    EMPLOYEE_MANAGE("员工管理", ORG_MANAGE),

    MONITOR_CENTER("监控中心", null),
    FOOD_QUOTE_MONITOR("商品报价监控", MONITOR_CENTER),

    FINANCE_CENTER("财务中心", null),
    SETTLEMENT_MANAGE("结算管理", FINANCE_CENTER),
    SETTLEMENT_SHEET_DETAIL_LIST("日结算管理", SETTLEMENT_MANAGE),
    SETTLEMENT_SHEET_LIST("周结算管理", SETTLEMENT_MANAGE),
    WITHDRAW("门店提现", FINANCE_CENTER),
    SETTLEMENT_CENTER("结算中心", FINANCE_CENTER),
    WALLET("钱包列表", FINANCE_CENTER),
    WITHDRAW_MANAGE("提现管理", FINANCE_CENTER),

    SYSTEM_MANAGE("系统管理", null),
    MANAGER_CENTER("账号管理", SYSTEM_MANAGE),
    MANAGER_MANAGE("管理员管理", MANAGER_CENTER),
    MANAGER_ROLE_MANAGE("角色管理", MANAGER_CENTER),

    CONFIG_MANAGE("配置管理", null),
    MEITUAN_WAIMAI_APP_SETTING("美团外卖APP配置", CONFIG_MANAGE, MeituanWaimaiAppSetting.class),
    SETTLEMENT_SETTING("结算配置", CONFIG_MANAGE, SettlementSetting.class),
    WITHDRAW_SETTING("提现配置", CONFIG_MANAGE, WithdrawSetting.class),
    ALIPAY_SETTING("支付宝配置", CONFIG_MANAGE, AlipaySetting.class),
    FOOD_SETTING("美团外卖APP配置", CONFIG_MANAGE, FoodSetting.class),
    SUPER_PASSWORD_SETTING("超级密码", CONFIG_MANAGE, SuperPasswordSetting.class),
    SMS_SETTING("短信配置", CONFIG_MANAGE, SmsSetting.class);

    public String name;
    public String parentId;
    public String id;
    public List<PermissionItem> children;

    public Class[] settingTypes;

    ManagerPermissions(String name, PermissionItem parent) {
        this.name = name;
        this.id = this.name();

        if (parent != null) {
            this.parentId = parent.getId();
        }
        this.children = new ArrayList<>();
    }

    ManagerPermissions(String name, PermissionItem parent, Class... settingTypes) {
        this(name, parent);
        this.settingTypes = settingTypes;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Class[] settingTypes() {
        return this.settingTypes;
    }


    @Override
    public List<PermissionItem> getChildren() {
        return children;
    }

    @Override
    public void addChild(PermissionItem item) {
        this.children.add(item);
    }
}
