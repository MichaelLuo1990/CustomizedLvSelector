package com.michael.customizedlvdemo.checkboxstyle.horizontal;

import java.util.List;

/**
 * Created by michaelluo on 17/7/14.
 *
 * @desc 父节点
 */

public class CBHorizontalGroupEntity {
    private String groupName;
    private List<CBHorizontalChildEntity> cbHorizontalChildEntities;
    private boolean isCheck;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<CBHorizontalChildEntity> getCbHorizontalChildEntities() {
        return cbHorizontalChildEntities;
    }

    public void setCbHorizontalChildEntities(List<CBHorizontalChildEntity> cbHorizontalChildEntities) {
        this.cbHorizontalChildEntities = cbHorizontalChildEntities;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
