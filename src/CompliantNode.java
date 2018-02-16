import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//See https://www.coursera.org/learn/cryptocurrency/lecture/SKxAO/the-bitcoin-network for more info on how to do the assignment

/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node {
	
	private double p_graph = 0;
	private double p_malicious = 0; 
	private double p_txDistribution = 0; 
	private int numRounds = 0;
	
	private boolean[] followees = new boolean[100000];
	private boolean[] malicious_followees = new boolean[100000]; // This is a very ugly way... but just to go through the check on coursera quickly
	private Set<Transaction> pendingTransactions;

    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
    	//Assign values in constructor
    	this.p_graph = p_graph;
    	this.p_malicious = p_malicious;
    	this.p_txDistribution = p_txDistribution;
    	this.numRounds = numRounds;
    	
    	//Assign rest of values
    	this.pendingTransactions = new HashSet<Transaction>();
    	
    	//Initialise all followees as trusted
        for (int i=0; i<this.malicious_followees.length; i++){
        	//If it is my a followee of mine
        	this.malicious_followees[i] = false;
        }
       
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
    	// Add the set of candidate transactions I receive to my list - with this is already 80% score /// COMMENT IF TRYING THE MORE SOPHISTICATED APPROACH
        /*for (Candidate c : candidates) {
			this.pendingTransactions.add(c.tx);
		}*/
        
        // Basic detection of malicious nodes: add them to a list of untrusted followees if it is not in any of the candidate transactions STILL 80%
        for (int i=0; i<this.followees.length; i++){
        	//If it is a followee of mine
        	if (this.followees[i]){
        		//Going through set manually
        		boolean is_malitious = true;
        		for (Candidate c : candidates) {
					if(c.sender==i){
						is_malitious = false;
					}	
				}
        		this.malicious_followees[i] = is_malitious;
        	}
        }
        
        // Now I add only that transaction if I actually trust that followee
        for (Candidate c : candidates) {
        	if (!this.malicious_followees[c.sender]){
    			this.pendingTransactions.add(c.tx);
        	}
		}
    }
}
