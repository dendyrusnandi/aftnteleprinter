package displays.pid;

import java.util.Comparator;

class PlayerComparatorRejATS1_S implements Comparator {
		public static final int ID = 0;
		public static final int FT = 1;
		public static final int ORG = 2;	
		public static final int TBNM = 3;	
		public static final int MSG = 4;	
		public static final int DTM = 5;	
		public static final int ID_T = 6;	
		public static final int TABEL = 7;	
		
		public static final int ASCENDING = 0;
		public static final int DESCENDING = 1;
		
		private int column;
		private int direction;
		
		/**
		 * Compares two Player objects
		 * 
		 * @param obj1 the first Player
		 * @param obj2 the second Player
		 * @return int
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		
		public int compare(Object obj1, Object obj2) {
			int rc = 0;
		    PlayerRejATS1_S p1 = (PlayerRejATS1_S) obj1;
		    PlayerRejATS1_S p2 = (PlayerRejATS1_S) obj2;
		
		    // Determine which field to sort on, then sort on that field
		    switch (column) {
		    	case ID:
		    		rc = p1.getID().compareTo(p2.getID());
		    		break;
			    case FT:
			    	rc = p1.getFT().compareTo(p2.getFT());
			    	break;
			    case ORG:
			    	rc = p1.getORG().compareTo(p2.getORG());
			    	break;
			    case TBNM:
			    	rc = p1.getTBNM().compareTo(p2.getTBNM());
			    	break;
			    case MSG:
			    	rc = p1.getMSG().compareTo(p2.getMSG());
			    	break;
			    case DTM:
			    	rc = p1.getDTM().compareTo(p2.getDTM());
			    	break;
			    case ID_T:
			    	rc = p1.getID_T().compareTo(p2.getID_T());
			    	break;
			    case TABEL:
			    	rc = p1.getTABEL().compareTo(p2.getTABEL());
			    	break;
		    }
		
		    // Check the direction for sort and flip the sign if appropriate
		    if (direction == ASCENDING) 
		    	rc = -rc;
		    return rc;
		}
		public void setColumn(int column) {
			this.column = column;
		}
		
		// Sets the direction for sorting
		public void setDirection(int direction) {
			this.direction = direction;
		}	
		
		// Reverses the direction
		public void reverseDirection() {
			direction = 1 - direction;
		}

}
