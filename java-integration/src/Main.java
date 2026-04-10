import ui.LoginUI;
import ui.MainUI;

//Main Entry Point that Launches the Academic Management System GUI

public class Main {
    public static void main(String[] args) {
        try {
            // Launch GUI in Event Dispatch Thread
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    // Show login page first
                    new LoginUI(new Runnable() {
                        @Override
                        public void run() {
                            // After successful login, launch main UI
                            new MainUI();
                            System.out.println("Application started successfully!");
                        }
                    });
                }
            });
        } catch (Exception e) {
            System.err.println("ERROR: Failed to start application!");
            e.printStackTrace();
        }
    }
}
