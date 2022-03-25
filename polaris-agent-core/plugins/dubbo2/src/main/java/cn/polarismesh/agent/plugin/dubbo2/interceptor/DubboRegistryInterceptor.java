package cn.polarismesh.agent.plugin.dubbo2.interceptor;

import cn.polarismesh.agent.plugin.dubbo2.polaris.PolarisRegistryFactory;
import cn.polarismesh.agent.plugin.dubbo2.utils.ReflectUtil;
import cn.polarismesh.common.interceptor.AbstractInterceptor;
import org.apache.dubbo.registry.RegistryFactory;
import org.apache.dubbo.registry.integration.InterfaceCompatibleRegistryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * interceptor for org.apache.dubbo.registry.integration.RegistryProtocol#setRegistryFactory(org.apache.dubbo.registry.RegistryFactory)
 */
public class DubboRegistryInterceptor implements AbstractInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboRegistryInterceptor.class);

    @Override
    public void before(Object target, Object[] args) {
    }

    /**
     * RegistryProtocol 的 setRegistryFactory方法
     * 替换registryFactory为PolarisRegistryFactory
     */
    @Override
    public void after(Object target, Object[] args, Object result, Throwable throwable) {
        LOGGER.debug("[POLARIS] set {}.registryFactory as PolarisRegistryFactory", target.getClass());
        if(target instanceof InterfaceCompatibleRegistryProtocol){
            ReflectUtil.setSuperValueByFieldName(target, "registryFactory", new PolarisRegistryFactory((RegistryFactory) args[0]));
        }else{
            ReflectUtil.setValueByFieldName(target, "registryFactory", new PolarisRegistryFactory((RegistryFactory) args[0]));
        }
    }
}