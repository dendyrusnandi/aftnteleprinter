package displays.pid;

import java.util.Comparator;

class PlayerComparatorOutATS1_S implements Comparator {
		public static final int FT = 0;
		public static final int ST = 1;
		public static final int DTM = 2;	
		public static final int ARCID = 3;	
		public static final int TYP = 4;	
		public static final int ORG = 5;	
		public static final int MSG = 6;	
		public static final int ID = 7;	
		public static final int TABEL = 8;	
		public static final int DATETIME = 9;	
		
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
		    PlayerOutATS1_S p1 = (PlayerOutATS1_S) obj1;
		    PlayerOutATS1_S p2 = (PlayerOutATS1_S) obj2;
		
		    // Determine which field to sort on, then sort on that field
		    switch (column) {
		    	case FT:
		    		rc = p1.getFT().compareTo(p2.getFT());
		    		break;
			    case ST:
			    	rc = p1.getST().compareTo(p2.getST());
			    	break;
			    case DTM:
			    	rc = p1.getDTM().compareTo(p2.getDTM());
			    	break;
			    case ARCID:
			    	rc = p1.getARCID().compareTo(p2.getARCID());
			    	break;
			    case TYP:
			    	rc = p1.getTYP().compareTo(p2.getTYP());
			    	break;
			    case ORG:
			    	rc = p1.getORG().compareTo(p2.getORG());
			    	break;
			    case MSG:
			    	rc = p1.getMSG().compareTo(p2.getMSG());
			    	break;
			    case ID:
			    	rc = p1.getID().compareTo(p2.getID());
			    	break;
			    case TABEL:
			    	rc = p1.getTABEL().compareTo(p2.getTABEL());
			    	break;
			    case DATETIME:
			    	rc = p1.getDATETIME().compareTo(p2.getDATETIME());
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
