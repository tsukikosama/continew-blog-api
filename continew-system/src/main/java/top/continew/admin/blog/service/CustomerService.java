package top.continew.admin.blog.service;

import top.continew.admin.blog.model.req.CustomerLoginReq;
import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.blog.model.query.CustomerQuery;
import top.continew.admin.blog.model.req.CustomerReq;
import top.continew.admin.blog.model.resp.CustomerDetailResp;
import top.continew.admin.blog.model.resp.CustomerResp;

/**
 * 用户业务接口
 *
 * @author weilai
 * @since 2025/07/09 15:15
 */
public interface CustomerService extends BaseService<CustomerResp, CustomerDetailResp, CustomerQuery, CustomerReq> {
    Void register(CustomerReq req);

    String loginCustomer(CustomerLoginReq req);

}