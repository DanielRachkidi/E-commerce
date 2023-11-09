using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace E_commerce.DataModel
{
    [Table("userinfo", Schema = "public")]
    public class UserInfo
    {
        [Key]
        [Column("id_userinfo")]
        public int Id { get; set; }

        [Column("address")]
        public string Address { get; set; }

        [Column("city")]
        public string City { get; set; }

        [Column("code_postal")]
        public int CodePostal { get; set; }

        [Column("country")]
        public string Country { get; set; }

        [ForeignKey("user")]
        [Column("id_user")]
        public int UserId { get; set; }

    }
}
