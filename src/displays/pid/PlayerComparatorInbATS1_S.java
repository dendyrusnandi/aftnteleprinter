package displays.pid;

import java.util.Comparator;

class PlayerComparatorInbATS1_S implements Comparator {
		public static final int ID = 0;
		public static final int MSG = 1;
		public static final int MSG2 = 2;
		public static final int TGL = 3;	
		
		public static final int ASCENDING = 0;
		public static final int DESCENDING = 1;
		
		private int column;
		private int direction;
		
		public int compare(Object obj1, Object obj2) {
			int rc = 0;
		    PlayerInbATS1_S p1 = (PlayerInbATS1_S) obj1;
		    PlayerInbATS1_S p2 = (PlayerInbATS1_S) obj2;
		    switch (column) {
		    	case ID:
		    		rc = p1.getID().compareTo(p2.getID());
		    		break;
			    case MSG:
			    	rc = p1.getMSG().compareTo(p2.getMSG());
			    	break;
			    case MSG2:
			    	rc = p1.getMSG2().compareTo(p2.getMSG2());
			    	break;
			    case TGL:
			    	rc = p1.getTGL().compareTo(p2.getTGL());
			    	break;
		    }
		    if (direction == ASCENDING) 
		    	rc = -rc;
		    return rc;
		}
		public void setColumn(int column) {
			this.column = column;
		}
		
		public void setDirection(int direction) {
			this.direction = direction;
		}	
		
		public void reverseDirection() {
			direction = 1 - direction;
		}
}
