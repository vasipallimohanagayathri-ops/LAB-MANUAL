import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class StudentRegistration extends JFrame implements ActionListener {
    private JTextField txtUsn, txtName;
    private JComboBox<String> cbBranch;
    private JRadioButton rbMale, rbFemale;
    private ButtonGroup bgGender;
    private JCheckBox chkJava, chkPython;
    private JButton btnSubmit, btnClear;
    private JTextArea taDetails;

    public StudentRegistration() {
        setTitle("Student Registration Form");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); 
        JLabel lblTitle = new JLabel("STUDENT REGISTRATION FORM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setBounds(110, 10, 250, 20);
        add(lblTitle);
        JLabel lblUsn = new JLabel("USN");
        lblUsn.setBounds(30, 50, 60, 20);
        add(lblUsn);
        JLabel lblUsnColon = new JLabel(":");
        lblUsnColon.setBounds(90, 50, 10, 20);
        add(lblUsnColon);
        txtUsn = new JTextField();
        txtUsn.setBounds(110, 50, 180, 25);
        add(txtUsn);
        JLabel lblName = new JLabel("Name");
        lblName.setBounds(30, 90, 60, 20);
        add(lblName);
        JLabel lblNameColon = new JLabel(":");
        lblNameColon.setBounds(90, 90, 10, 20);
        add(lblNameColon);
        txtName = new JTextField();
        txtName.setBounds(110, 90, 180, 25);
        add(txtName);
        JLabel lblBranch = new JLabel("Branch");
        lblBranch.setBounds(30, 130, 60, 20);
        add(lblBranch);
        JLabel lblBranchColon = new JLabel(":");
        lblBranchColon.setBounds(90, 130, 10, 20);
        add(lblBranchColon);
        String[] branches = {"Computer Science", "Information Science", "Electronics", "Mechanical"};
        cbBranch = new JComboBox<>(branches);
        cbBranch.setBounds(110, 130, 180, 25);
        add(cbBranch);
        JLabel lblGender = new JLabel("Gender");
        lblGender.setBounds(30, 170, 60, 20);
        add(lblGender);
        JLabel lblGenderColon = new JLabel(":");
        lblGenderColon.setBounds(90, 170, 10, 20);
        add(lblGenderColon);
        
        rbMale = new JRadioButton("Male");
        rbMale.setBounds(110, 170, 70, 20);
        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(190, 170, 80, 20);
        bgGender = new ButtonGroup();
        bgGender.add(rbMale);
        bgGender.add(rbFemale);
        
        add(rbMale);
        add(rbFemale);
        JLabel lblSkills = new JLabel("Skills");
        lblSkills.setBounds(30, 210, 60, 20);
        add(lblSkills);
        JLabel lblSkillsColon = new JLabel(":");
        lblSkillsColon.setBounds(90, 210, 10, 20);
        add(lblSkillsColon);
        
        chkJava = new JCheckBox("Java");
        chkJava.setBounds(110, 210, 70, 20);
        chkPython = new JCheckBox("Python");
        chkPython.setBounds(190, 210, 80, 20);
        
        add(chkJava);
        add(chkPython);
        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(100, 260, 90, 30);
        btnClear = new JButton("Clear");
        btnClear.setBounds(210, 260, 90, 30);
        
        add(btnSubmit);
        add(btnClear);
        taDetails = new JTextArea();
        taDetails.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taDetails);
        scrollPane.setBounds(30, 310, 370, 120);
        add(scrollPane);
        btnSubmit.addActionListener(this);
        btnClear.addActionListener(this);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            String usn = txtUsn.getText().trim();
            String name = txtName.getText().trim();
            if (usn.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "USN and Name fields cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String branch = cbBranch.getSelectedItem().toString();
            
            String gender = "Not Specified";
            if (rbMale.isSelected()) gender = "Male";
            else if (rbFemale.isSelected()) gender = "Female";

            StringBuilder skills = new StringBuilder();
            if (chkJava.isSelected()) skills.append("Java ");
            if (chkPython.isSelected()) skills.append("Python ");
            if (skills.length() == 0) skills.append("None");
            taDetails.setText("--------------------------------------------------\n");
            taDetails.append("Student Details\n");
            taDetails.append("--------------------------------------------------\n");
            taDetails.append("USN\t: " + usn + "\n");
            taDetails.append("Name\t: " + name + "\n");
            taDetails.append("Branch\t: " + branch + "\n");
            taDetails.append("Gender\t: " + gender + "\n");
            taDetails.append("Skills\t: " + skills.toString().trim() + "\n");

        } else if (e.getSource() == btnClear) {
            // 9. Clear all fields
            txtUsn.setText("");
            txtName.setText("");
            cbBranch.setSelectedIndex(0);
            bgGender.clearSelection();
            chkJava.setSelected(false);
            chkPython.setSelected(false);
            taDetails.setText("");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentRegistration());
    }
}
