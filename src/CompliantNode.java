import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node {
	
	private double p_graph = 0;
	private double p_malicious = 0; 
	private double p_txDistribution = 0; 
	private int numRounds = 0;
	
	private boolean[] followees;
	//private boolean[] untrusted_followees;
	
	
	private Set<Transaction> pendingTransactions;
	
	//private Set<Candidate> candidates;

    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
    	//Assign values in constructor
    	this.p_graph = p_graph;
    	this.p_malicious = p_malicious;
    	this.p_txDistribution = p_txDistribution;
    	this.numRounds = numRounds;
    	
    	//Assign rest of values
    	this.pendingTransactions = new HashSet<Transaction>();
       
    }

    public void setFollowees(boolean[] followees) {
        //Assign list of followees
    	this.followees = followees;
    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
        //Assign list of transactions
    	for (Transaction tx : pendingTransactions) {
			this.pendingTransactions.add(tx);
		}
    }

    public Set<Transaction> sendToFollowers() {
        // Broadcast my list of transactions to all my followers
    	return pendingTransactions;
    }

    public void receiveFromFollowees(Set<Candidate> candidates) {
    	// Add the set of candidate transactions I receive to my list
        for (Candidate c : candidates) {
			this.pendingTransactions.add(c.tx);
		}
    }
}
