using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;


namespace Student.Model
{
    [Table("product", Schema = "public")]
    public class Product
    {
        [Key]
        [Column("id_product")]
        public int Id { get; set; }

        [Column("name")]
        [Required(ErrorMessage = "Name is required")]
        public string Name { get; set; }

        [Column("price")]
        [Required(ErrorMessage = "Price is required")]
        [Range(0, double.MaxValue, ErrorMessage = "Price cannot be negative")]
        public double Price { get; set; }

        [Column("quantity")]
        [Required(ErrorMessage = "Quantity is required")]
        [Range(0, int.MaxValue, ErrorMessage = "Quantity cannot be negative")]
        public int Quantity { get; set; }

        [Column("size")]
        public string Size { get; set; }

        [Column("gender")]
        [RegularExpression("^(man|women)$", ErrorMessage = "Invalid gender. Must be 'man' or 'women'")]
        public string Gender { get; set; }

        public override string ToString()
        {
            return $"Product{{ id={Id}, name='{Name}', price={Price} }}";
        }
    }
}