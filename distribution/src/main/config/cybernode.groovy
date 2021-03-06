/*
 * Configuration for a Cybernode
 */
import org.rioproject.config.Component

import org.rioproject.resources.client.JiniClient

import net.jini.core.discovery.LookupLocator
import org.rioproject.config.Constants

/*
 * Declare Cybernode properties
 */
@Component('org.rioproject.cybernode')
class CybernodeConfig {
    String serviceName = 'Cybernode'
    String serviceComment = 'Dynamic Agent'
    String jmxName = 'org.rioproject.cybernode:type=Cybernode'
    boolean provisionEnabled = true
    //long provisionerLeaseDuration = 1000*60

    String[] getInitialLookupGroups() {
        def groups = [System.getProperty(Constants.GROUPS_PROPERTY_NAME,
                      System.getProperty('user.name'))]
        return groups as String[]
    }

    LookupLocator[] getInitialLookupLocators() {
        String locators = System.getProperty(Constants.LOCATOR_PROPERTY_NAME)
        if(locators!=null) {
            def lookupLocators = JiniClient.parseLocators(locators)
            return lookupLocators as LookupLocator[]
        } else {
            return null
        }
    }

    String getServiceLogRootDirectory() {
        String logExt = System.getProperty(Constants.GROUPS_PROPERTY_NAME, System.getProperty('user.name'))
        String serviceLogRootDirectory = System.getProperty("RIO_LOG_DIR")
        if(serviceLogRootDirectory==null) {
            String opSys = System.getProperty('os.name')
            String rootLogDir = opSys.startsWith("Windows")?System.getProperty("java.io.tmpdir"):'/tmp'
            String name = System.getProperty('user.name')
            serviceLogRootDirectory = rootLogDir+'/'+name+'/logs/'+logExt
        }
        return serviceLogRootDirectory
    }

    String getNativeLibDirectory() {
        return System.getProperty("RIO_NATIVE_DIR")
    }
}