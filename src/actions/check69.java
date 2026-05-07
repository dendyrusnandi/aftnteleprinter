package actions;

/*
MESSAGE NOTAM 
sQ = FROM NOTAM SERIE UNTIL BEFORE ITEM E 
sE = ITEM E
sO = AFTER ITEM E UNTIL END MESSAGE
*/

public class check69 {
	String sQ,sE,sO;
	int i_maxMsg=69;
	public String sEres[];
	int i_temp;
	int i_nType;
	int i_mType;
	int i_nEO;
	public int i_totNotam;
	  
	public void check(String sQ, String sE, String sO, int i){
		//System.out.println("lnA:"+sQ.length());
		//System.out.println("lnE:"+sE.length());
		//System.out.println("lnO:"+sO.length());
		
		i_temp=i_maxMsg-sQ.length();
		  
		i_nType=sE.length()/i_temp;
		//System.out.println("i_nType:"+i_nType);
		sEres = new String[i_nType+100];
		
		int iCnt=1;
		int iLengthTot=0;
		for(iCnt=1;iCnt<i_nType+1;iCnt++) {
			sEres[iCnt-1] = sE.substring((iCnt-1)*i_temp, iCnt*i_temp);
			iLengthTot+=sEres[iCnt-1].length();
		}
		
		i_mType=sE.length()%i_temp;
		//System.out.println("sisa E ln:"+i_mType+" totLength"+iLengthTot);
		if (i_nType>=1) {
			sEres[iCnt-1] = sE.substring(iLengthTot);
		}
		else sEres[iCnt-1] = sE;

		String sSumE_O = sQ+sEres[iCnt-1]+sO;
		String sTempIcnt = sEres[iCnt-1];

		//System.out.println("sisa E lnTot:"+sSumE_O.length()+" iCnt:"+iCnt+" i_temp"+i_temp);
		//int iCnt1=iCnt;
		if (sSumE_O.length()>i_maxMsg) {
			sEres[iCnt-1] = sEres[iCnt-1].substring(0, i_maxMsg-(sQ.length()+sO.length()));
			iCnt++;
			sEres[iCnt-1] = sTempIcnt.substring(i_maxMsg-(sQ.length()+sO.length()));
		}
		//System.out.println("result:");
		for (int iCount=0;iCount<iCnt;iCount++) {
			//System.out.println(iCount+":"+sEres[iCount]+" ln:"+sEres[iCount].length());
		}
		i_totNotam=iCnt;
	}
}
