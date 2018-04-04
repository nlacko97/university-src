using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Basketball
{
    public partial class LoginForm : Form
    {
        public LoginForm()
        {
            InitializeComponent();
        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            DBConnection conn = new DBConnection();
            conn.CreateConnection();
            conn.Connect();

            SHA1Managed sha1 = new SHA1Managed();
            byte[] hash = sha1.ComputeHash(Encoding.UTF8.GetBytes(textBox2.Text));
            var sb = new StringBuilder(40);

            foreach (byte b in hash)
            {
                sb.Append(b.ToString("x2"));
            }

            string hashed = sb.ToString();
            //string hashed = BitConverter.ToString(hash);


            string cmdText = "SELECT * FROM users WHERE password=@pwd";
            //MessageBox.Show(cmdText);
            SqlCommand cmd = new SqlCommand(cmdText);
            cmd.Connection = conn.connection;
            cmd.Parameters.AddWithValue("@uname", textBox1.Text);
            cmd.Parameters.AddWithValue("@pwd", hashed);
            //textBox2.Text = hashed;
            System.Console.Out.WriteLine(hashed);
            SqlDataReader reader = cmd.ExecuteReader();
            if (reader.HasRows)
                MessageBox.Show("correct");
            
            conn.Close();
        }
    }
}
