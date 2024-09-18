namespace LabOne;

public class Module3 : Form
{
    private readonly Form _mainForm;
    private readonly ListBox _listBox;
    
    public Module3 (Form mainForm)
    {
        this._mainForm = mainForm;
        this.Text = "Робота2";
        this.Width = 300;
        this.Height = 400;

        _listBox = new ListBox();

        _listBox.Dock = DockStyle.Top;
        _listBox.Height = this.ClientSize.Height * 2 / 3;

        Button yesButton = new Button
        {
            Text = "Так", 
            Left = this.ClientSize.Width/6, 
            Height = 30, 
            Top = 300,
            Anchor = AnchorStyles.Bottom | AnchorStyles.Left
        };
        Button cancelButton = new Button
        {
            Text = "Відміна", 
            Left = this.ClientSize.Width*3/6, 
            Height = 30,
            Top = 300,
            Anchor = AnchorStyles.Bottom | AnchorStyles.Right
        };
        
        this.Resize += (s, e) =>
        {
            _listBox.Height = this.ClientSize.Height * 2 / 3;
        };
            
        yesButton.Click += yesButton_Click;
        cancelButton.Click += (s, args) => { this.Close(); };
        
        this.Controls.Add(_listBox);
        this.Controls.Add(yesButton);
        this.Controls.Add(cancelButton);

        this.Load += Module3_Load;
    }
    
    private void yesButton_Click(object sender, EventArgs e)
    {
        if (_listBox.SelectedItem != null)
        {
            ((Form1)_mainForm).SetLabelText(_listBox.SelectedItem.ToString());
            this.Close();
        }
        else 
        {
            MessageBox.Show("Виберіть елемент");
        }
    }

    private void Module3_Load(object sender, EventArgs e)
    {
        string filePath = "groups.txt";
        if (File.Exists(filePath)) 
        {
            string fileContent = File.ReadAllText(filePath);
            string[] items = fileContent.Split(' ');

            foreach (string item in items)
            {
                if (!string.IsNullOrWhiteSpace(item)) _listBox.Items.Add(item);
            }
        }
        else MessageBox.Show("File not found");
    }
}