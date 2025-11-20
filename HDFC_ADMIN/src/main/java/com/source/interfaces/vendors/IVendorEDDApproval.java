package com.source.interfaces.vendors;

import java.util.List;

public interface IVendorEDDApproval {
    List<String> clusterHeadAssignApprovers();
    String savePendingApprover();
    void eddFullApprove(String approver);
    void approversApproveEDD(List<String> approvers);
    void verifyEddIsApproved();
}
