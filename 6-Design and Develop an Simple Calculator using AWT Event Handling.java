import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator extends Frame implements ActionListener {
    private TextField txtNum1, txtNum2;
    private Label lblResult;
    private Button btnAdd, btnSub, btnMul, btnDiv, btnClear;

    public SimpleCalculator() {
        setLayout(new FlowLayout());
        setTitle("Simple Calculator");
        add(new Label("First Number :"));
        txtNum1 = new TextField(10);
        add(txtNum1);

        add(new Label("Second Number :"));
        txtNum2 = new TextField(10);
        add(txtNum2);
        btnAdd = new Button("Add");
        btnSub = new Button("Subtract");
        btnMul = new Button("Multiply");
        btnDiv = new Button("Divide");
        btnClear = new Button("Clear");
        add(btnAdd);
        add(btnSub);
        add(btnMul);
        add(btnDiv);
        add(btnClear);
        lblResult = new Label("Result :                                 ");
        add(lblResult);
        btnAdd.addActionListener(this);
        btnSub.addActionListener(this);
        btnMul.addActionListener(this);
        btnDiv.addActionListener(this);
        btnClear.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        setSize(320, 220);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClear) {
            txtNum1.setText("");
            txtNum2.setText("");
            lblResult.setText("Result : ");
            return;
        }

        try {
            double n1 = Double.parseDouble(txtNum1.getText().trim());
            double n2 = Double.parseDouble(txtNum2.getText().trim());

            if (e.getSource() == btnAdd) {
                lblResult.setText("Result : " + (n1 + n2));
            } else if (e.getSource() == btnSub) {
                lblResult.setText("Result : " + (n1 - n2));
            } else if (e.getSource() == btnMul) {
                lblResult.setText("Result : " + (n1 * n2));
            } else if (e.getSource() == btnDiv) {
                if (n2 == 0) {
                    lblResult.setText("Result : Cannot divide by zero");
                } else {
                    lblResult.setText("Result : " + (n1 / n2));
                }
            }
        } catch (NumberFormatException ex) {
            lblResult.setText("Result : Invalid Input");
        }
    }    public static void main(String[] args) {
        new SimpleCalculator();
    }
}
