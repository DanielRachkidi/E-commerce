using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace E_commerce.DataModel
{
    [Table("userpayment", Schema = "public")]
    public class UserPayment
    {
        [Key]
        [Column("id_payment")]
        public int Id { get; set; }

        [Column("payment_type")]
        public string PaymentType { get; set; }

        [Column("provider")]
        public string Provider { get; set; }

        [Column("accountno")]
        public int AccountNumber { get; set; }

        [Column("expiry")]
        public DateTime Expiry { get; set; }

        [ForeignKey("user")]
        [Column("id_user")]
        public int UserId { get; set; }
    }
}
