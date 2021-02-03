package Assignment3;


public class Vote {
	
	Player p;
	private static Node[] voteIndexing = new Node[Player.PlayerList.size()];
	public Vote() { 
		for(int i = 0 ; i<Player.PlayerList.size() ; i++) {
			p = Player.getPlayer(i);
			voteIndexing[i] = new Node(i+1,0,p.isVotedOut());
		}
	}
	
	public static void giveVote(int playerId) {
		voteIndexing[playerId].voteCount += 1; 
		
	}
	public static boolean hasVoteClashing() {
		//int highest = 0;
		mergeSort(voteIndexing, 0, voteIndexing.length-1);
		//sorted in decreasing order
		int start = 0;
		int repetition = 0;
		while(start < voteIndexing.length-1) {
			if(voteIndexing[start] == voteIndexing[start+1]) {
				repetition++;
			}
			else {
				break;
			}
			start++;
		}
	
		return repetition > 1;
	}
	public static void clearVotes() {
		for(int i = 0 ; i <voteIndexing.length ; i++) {
			voteIndexing[i].voteCount = 0;
		}
	}
	public static Player getMaximumVoted() {
		int index = voteIndexing[0].pid;
		Player p_index = Player.getPlayer(index-1);
		return p_index;
	}
	public static boolean canHeVote(int voted) {
		if(Player.getPlayer(voted).isVotedOut()) {
			return  false;
		}
		return true;
	}
	public static void mergeSort(Node a[], int beg, int end)  
	{  
	    int mid;  
	    if(beg<end)  
	    {  
	        mid = (beg+end)/2;  
	        mergeSort(a,beg,mid);  
	        mergeSort(a,mid+1,end);  
	        merge(a,beg,mid,end);  
	    }  
	}  
	public static void merge(Node a[], int beg, int mid, int end)  
	{  
	    int i=beg,j=mid+1,k,index = beg;  
	    Node temp[] = new Node[1000];  
	    
	    while(i<=mid && j<=end)  
	    {  
	        if(a[i].voteCount > a[j].voteCount)  
	        {  
	            temp[index] = a[i];  
	            i = i+1;  
	        }  
	        else   
	        {  
	            temp[index] = a[j];  
	            j = j+1;   
	        }  
	        index++;  
	    }  
	    if(i>mid)  
	    {  
	        while(j<=end)  
	        {  
	            temp[index] = a[j];  
	            index++;  
	            j++;  
	        }  
	    }  
	    else   
	    {  
	        while(i<=mid)  
	        {  
	            temp[index] = a[i];  
	            index++;  
	            i++;  
	        }  
	    }  
	    k = beg;  
	    while(k<index)  
	    {  
	        a[k]=temp[k];  
	        k++;  
	    }  
	}
	
}
class Node{
	public Node(int pid , int v , boolean vo) {
		this.votedOut = vo;
		this.pid = pid;
		voteCount = v;
	}
	boolean votedOut;
	int pid;
	int voteCount;
	public String toString() {
		return "pid : "+pid+" votecount : "+voteCount;
	}
}