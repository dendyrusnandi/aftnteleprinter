package setting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.*;

public class TextSetting {

	public TextSetting() {
		
	}

	
	public void nosymbols(Text text) {
		text.addVerifyListener(new VerifyListener() {
	        @Override
	        public void verifyText(VerifyEvent event) {
	            event.text = event.text.replaceAll("[^A-Za-z0-9]", "");
	        }
	    });
	}	
	
	public void hexadecimal(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'F') && !('a' <= chars2[i] && chars2[i] <= 'f') && !('0' <= chars2[i] && chars2[i] <= '9') && !(chars2[i]=='\n')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void per(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'E') && !('a' <= chars2[i] && chars2[i] <= 'e') && !(chars2[i]=='H') && !(chars2[i]=='h') && !(chars2[i]=='\n')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void alphanum(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'Z') && !('a' <= chars2[i] && chars2[i] <= 'z') && !('0' <= chars2[i] && chars2[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void alphanumEnter(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'Z') && !('a' <= chars2[i] && chars2[i] <= 'z') && !('0' <= chars2[i] && chars2[i] <= '9') && !(chars2[i]=='\n')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void alphanum1(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'Z') && !('a' <= chars2[i] && chars2[i] <= 'z') && !('0' <= chars2[i] && chars2[i] <= '9') && !(chars2[i]==',')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void alphaNumStroke(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'Z') && !('a' <= chars2[i] && chars2[i] <= 'z') && !('0' <= chars2[i] && chars2[i] <= '9') && !(chars2[i]=='/')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void alphaNumSpace(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'Z') && !('a' <= chars2[i] && chars2[i] <= 'z') && !('0' <= chars2[i] && chars2[i] <= '9') && !(chars2[i]==' ')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void alphaNumSTS(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'Z') && !('a' <= chars2[i] && chars2[i] <= 'z') && !('0' <= chars2[i] && chars2[i] <= '9') && !(chars2[i]==' ') && !(chars2[i]=='\n')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void alphaText(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'Z') && !('a' <= chars2[i] && chars2[i] <= 'z') && !('0' <= chars2[i] && chars2[i] <= '9') && !(chars2[i]=='/') && !(chars2[i]==' ')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void letter(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'Z')
							&& !('a' <= chars2[i] && chars2[i] <= 'z')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void letter18(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'Z') && !('a' <= chars2[i] && chars2[i] <= 'z') && !(chars2[i]=='\n')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void letterSpace(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'Z') && !('a' <= chars2[i] && chars2[i] <= 'z') && !(chars2[i]=='\n') && !(chars2[i]==' ')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void letter1(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars2 = new char[string.length()];
				string.getChars(0, chars2.length, chars2, 0);
				for (int i = 0; i < chars2.length; i++) {
					e.text = e.text.toUpperCase();
					if (!('A' <= chars2[i] && chars2[i] <= 'Z') && !('a' <= chars2[i] && chars2[i] <= 'z')) {
						e.doit = false;
						if (chars2[i]==',') e.doit = true; 
						return;
					}
				}
			}
		});
	}

	
	public void upper(Text text) {
		text.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				e.text = e.text.toUpperCase();
			}
		});
	}
	
	public void numeric(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
	
	public void numeric18(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9') && !(chars[i]=='\n')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}
}
