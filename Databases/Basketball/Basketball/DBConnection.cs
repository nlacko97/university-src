using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;



namespace Basketball
{
    class DBConnection
    {
        public SqlConnection connection = null;
        private static string connectionString = "Server=DESKTOP-RJLVA9M;Database=Basketball;Trusted_Connection=Yes";
       
        public void CreateConnection()
        {
            if (this.connection == null)
            {
                this.connection = new SqlConnection(connectionString);
            }
        }

        public void Connect()
        {
            CreateConnection();
            this.connection.Open();
        }

        public void Close()
        {
            this.connection.Close();
        }
    }
}
