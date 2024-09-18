namespace LabOne;

public class Module2 : Form
{
     private Form previousDialog; 
    public Module2(Form previousDialog)
    {
        this.previousDialog = previousDialog;
        
        this.Text = "?";
        this.Width = 400;
        this.Height = 200;
        
        Button backButton = new Button { Text = "<Назад", Top = 100,  Height = 30, Anchor = AnchorStyles.Bottom | AnchorStyles.Left };
        backButton.Left = (this.ClientSize.Width) / 3 - backButton.Width;
        
        Button yesButton = new Button { Text = "Так" , Top = 100, Left = backButton.Left + 100, Height = 30, Anchor = AnchorStyles.Bottom | AnchorStyles.None };
        Button cancelButton = new Button { Text = "Відміна", Top = 100, Left = yesButton.Left + 100 ,Height = 30, Anchor = AnchorStyles.Bottom | AnchorStyles.Right };
        
        yesButton.Click += (s, args) =>
        {
            Application.Exit();
        };
        cancelButton.Click += (s, args) => { this.Close(); };
        backButton.Click += (s, args) =>
        {
            this.previousDialog.Show();
            this.Close();
        };
        
        Label questionLabel2 = new Label()
        {
            Text = "Закрити програму?",
            AutoSize = true,
            Top = 20,
        };
        questionLabel2.Left = (this.ClientSize.Width - questionLabel2.Width) / 2;
        
        this.Controls.Add(yesButton);
        this.Controls.Add(backButton);
        this.Controls.Add(cancelButton);
        this.Controls.Add(questionLabel2);
        
        this.Resize += (s, e) =>
        {
            questionLabel2.Left = (this.ClientSize.Width - questionLabel2.Width) / 2;
        };
        
    }
}