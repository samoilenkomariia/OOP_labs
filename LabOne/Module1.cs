namespace LabOne;

public class Module1 : Form
{
    public Module1()
    {
        this.Text = "Робота1";
        this.Width = 300;
        this.Height = 150;

        Button nextButton = new Button { Text = "Далі>" , Left = 50, Top = 70, Width = 100, Height = 30 };
        Button cancelButton = new Button { Text = "Відміна", Left = 150, Top = 70, Width = 100, Height = 30 };
        
        nextButton.Anchor = AnchorStyles.Bottom | AnchorStyles.Right;
        cancelButton.Anchor = AnchorStyles.Bottom | AnchorStyles.Right;
        

        Label questionLabel = new Label()
        {
            Text = "Перейти далі?",
            AutoSize = true,
            Top = 20
        };
        
        questionLabel.Left = (this.ClientSize.Width - questionLabel.Width) / 2;
        
        this.Controls.Add(nextButton);
        this.Controls.Add(cancelButton);
        this.Controls.Add(questionLabel);
        
        this.Resize += (s, e) =>
        {
            questionLabel.Left = (this.ClientSize.Width - questionLabel.Width) / 2;
        };

        cancelButton.Click += (s, args) => { this.Close(); };
        nextButton.Click += NextButton_Click;

    }
    
    private void NextButton_Click(object sender, EventArgs e)
    {
        Module2 dialog = new Module2(this);
        this.Hide();
        dialog.ShowDialog();
    }
}