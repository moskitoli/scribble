/**
 *
 */
package li.moskito.scribble.rules.jcr;

import javax.jcr.Repository;
import javax.naming.Context;

import li.moskito.scribble.rules.RuleSetup;
import li.moskito.scribble.rules.RuleSetup.RequirementLevel;

/**
 * A {@link ContentRepository} implementation for providing a repository using a JNDI lookup. The repsitory is searched
 * used the configured Context.
 *
 * @author Gerald Muecke, gerald@moskito.li
 */
public class JNDIContentRepository extends ContentRepository {

    public JNDIContentRepository() {
        super(null);
    }

    /**
     * The lookup name used to resolve the the repository in the jndi context. The default lookup is
     * <code>java:/jcr/local</code>
     */
    private String lookupName = "java:/jcr/local";

    /**
     * A Naming context that is used if specified.
     */
    private Context context;

    @RuleSetup(RequirementLevel.OPTIONAL)
    public void setContext(final Context context) {
        assertNotInitialized();
        this.context = context;

    }

    /**
     * Sets the lookup name for the repository. The lookup name is looked up in the {@link Context} to resolve the
     * {@link Repository}. Default is &quot;java:/jcr/local&quot;
     *
     * @param lookupName
     *            the lookup name to use for jndi lookup
     */
    @RuleSetup(RequirementLevel.OPTIONAL)
    public void setLookupName(final String lookupName) {
        assertNotInitialized();
        this.lookupName = lookupName;
    }

    @Override
    protected Repository createRepository() throws Exception {
        return (Repository) context.lookup(lookupName);
    }

    @Override
    protected void destroyRepository() {

    }

}