/*
 * This configuration is used to start a Cybernode, including an embedded Webster
 */

import com.sun.jini.start.ServiceDescriptor
import org.rioproject.util.ServiceDescriptorUtil
import org.rioproject.config.Component
import org.rioproject.resolver.maven2.Repository

@Component('org.rioproject.start')
class StartCybernodeConfig {
    String[] getConfigArgs(String rioHome) {
        ServiceDescriptorUtil.checkForLoopback()
        def configArgs = [rioHome+'/config/common.groovy',
                          rioHome+'/config/cybernode.groovy',
                          rioHome+'/config/compute_resource.groovy']
        return configArgs as String[]
    }

    ServiceDescriptor[] getServiceDescriptors() {
        String m2Repo = Repository.getLocalRepository().absolutePath
        String rioHome = System.getProperty('RIO_HOME')
        def websterRoots = [rioHome+'/lib', ';',
                            rioHome+'/lib-dl', ';',
                            m2Repo]

        String policyFile = rioHome+'/policy/policy.all'

        def serviceDescriptors = [
            ServiceDescriptorUtil.getWebster(policyFile, '0', websterRoots as String[]),
            ServiceDescriptorUtil.getCybernode(policyFile, getConfigArgs(rioHome))
        ]
        return (ServiceDescriptor[])serviceDescriptors
    }

}