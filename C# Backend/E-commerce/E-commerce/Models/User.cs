using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json.Serialization;

namespace E_commerce.DataModel
{
    [Table("user", Schema = "public")]
    public class User
    {
        [Key]
        [Column("id_user")]
        public int Id { get; set; }

        [Column("username")]
        [MinLength(3, ErrorMessage = "Username should have min 3 characters")]
        [Required(ErrorMessage = "Name is required")]
        public string Username { get; set; }

        [Column("password")]
        [MinLength(6, ErrorMessage = "Password should have min 6 characters")]
        [RegularExpression("^(?=.*[0-9])(?=.*[a-z]).*$",
            ErrorMessage = "Password should contain at least one digit and one lowercase letter")]

        public string Password { get; set; }

        [Column("first_name")]
        public string FirstName { get; set; }

        [Column("last_name")]
        public string LastName { get; set; }

        [Column("telephone")]
        public int Telephone { get; set; }
        

    /*    [NotMapped]
        public string NormalizedPassword
        {
            get { return HashPassword(Password); }
        }

        private string HashPassword(string password)
        {
            using (SHA256 sha256 = SHA256.Create())
            {
                byte[] hashBytes = sha256.ComputeHash(Encoding.UTF8.GetBytes(password));
                return string.Concat(hashBytes.Select(b => b.ToString("x2")));
            }
        }
*/
  

        // Utility method to hash the password (replace this with your actual password hashing logic)
        public static string HashPassword(string password)
        {
            using (SHA256 sha256 = SHA256.Create())
            {
                byte[] hashBytes = sha256.ComputeHash(Encoding.UTF8.GetBytes(password));
                return ConvertToHexString(hashBytes);
            }
        }

        private static string ConvertToHexString(byte[] bytes)
        {
            StringBuilder builder = new StringBuilder();
            foreach (byte b in bytes)
            {
                builder.Append(b.ToString("x2"));
            }
            return builder.ToString();
        }
    }
}

