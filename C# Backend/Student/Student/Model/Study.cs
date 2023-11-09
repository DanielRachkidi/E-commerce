using System.ComponentModel.DataAnnotations;

namespace Student.Model
{
    public class Study
    {
        [Key]
        public int Id { get; set; }

        public string Name { get; set; }

        public int Age { get; set; }
    }
}
