package displays.pid;

class PlayerInbATS1_S {
	private String ID;
	private String MSG;
	private String TGL;
	private String MSG2;
	//private String TBNM;
	//private String TYP;
	//private String TGL;
			 
	public PlayerInbATS1_S(String ID, String MSG, String MSG2, String TGL) {
		this.ID = ID;
		this.MSG = MSG;
		this.MSG2 = MSG2;
		this.TGL = TGL;
	}
	
	public String getID() { return ID; }
	public String getMSG() { return MSG; }
	public String getTGL() { return TGL; }
	public String getMSG2() { return MSG2; }
}
