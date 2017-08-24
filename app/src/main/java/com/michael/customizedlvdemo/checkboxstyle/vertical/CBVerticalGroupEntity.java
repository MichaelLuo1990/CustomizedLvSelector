package com.michael.customizedlvdemo.checkboxstyle.vertical;

import java.util.List;

/**
 * Created by michaelluo on 17/7/14.
 *
 * @desc 父节点
 */

public class CBVerticalGroupEntity {
    private String groupName;
    private List<CBVerticalChildEntity> cbVerticalChildEntities;
    private boolean isCheck;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<CBVerticalChildEntity> getCbVerticalChildEntities() {
        return cbVerticalChildEntities;
    }

    public void setCbVerticalChildEntities(List<CBVerticalChildEntity> cbVerticalChildEntities) {
        this.cbVerticalChildEntities = cbVerticalChildEntities;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
