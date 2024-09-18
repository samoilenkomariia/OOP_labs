namespace LabOne;

public partial class Form1 : Form
{
    private Label displayLabel;
    public Form1()
    {
        InitializeComponent();
        this.Text = "Лабораторна робота №1";
        
        MenuStrip menuStrip = new MenuStrip();

        ToolStripMenuItem work1MenuItem = new ToolStripMenuItem("Робота1");
        ToolStripMenuItem work2MenuItem = new ToolStripMenuItem("Робота2");

        work1MenuItem.Click += Work1MenuItem_Click;
        work2MenuItem.Click += Work2MenuItem_Click;
         
        menuStrip.Items.Add(work1MenuItem);
        menuStrip.Items.Add(work2MenuItem);
        
        this.MainMenuStrip = menuStrip;
        this.Controls.Add(menuStrip);
        
        displayLabel = new Label
        {
            AutoSize = true,
            Font = new Font("Arial", 32, FontStyle.Regular),
        };
        displayLabel.Top = (this.ClientSize.Height ) / 2 - displayLabel.Height;
        displayLabel.Left = (this.ClientSize.Width ) / 2 - displayLabel.Width;

        this.Resize += (s, e) =>
        {
            displayLabel.Top = (this.ClientSize.Height - displayLabel.Height) / 2;
            displayLabel.Left = (this.ClientSize.Width - displayLabel.Width) / 2;
            float newFontSize = this.ClientSize.Width / 23f; 
            displayLabel.Font = new Font(displayLabel.Font.FontFamily, newFontSize, displayLabel.Font.Style);
        };
        
        this.Controls.Add(displayLabel);
    }
    
    private void Work1MenuItem_Click(object sender, EventArgs e)
    {
        Module1 dialog = new Module1();
        dialog.ShowDialog();
     
    }
    
    public void SetLabelText(string text)
    {
        displayLabel.Text = text;
    }
    private void Work2MenuItem_Click(object sender, EventArgs e)
    {
        Module3 dialog = new Module3(this);
        dialog.ShowDialog();
    }
}