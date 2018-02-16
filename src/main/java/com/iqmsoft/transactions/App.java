

package com.iqmsoft.transactions;

import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.transactions.TransactionsFraction;
import org.wildfly.swarm.msc.ServiceDeployment;
import org.wildfly.swarm.jaxrs.JaxRsDeployment;



public class App
{
    public static void main (String[] args) throws Exception 
    {
        Container container = new Container();

	/*
	 * Use specific TransactionFraction even though it doesn't do
	 * any more than the default one - for now.
	 */

        container.subsystem(new TransactionsFraction(4712, 4713));

        // Start the container

        container.start();

	/*
	 * Now register JAX-RS resource class.
	 */

	JaxRsDeployment appDeployment = new JaxRsDeployment();
        appDeployment.addResource(TestResource.class);

        container.deploy(appDeployment);
    }
}
