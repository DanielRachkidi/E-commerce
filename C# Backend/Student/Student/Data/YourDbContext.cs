using Microsoft.EntityFrameworkCore;
using Student.Model;

namespace Student.Data
{
    public class YourDbContext : DbContext
    {
        public YourDbContext(DbContextOptions<YourDbContext> options) : base(options)
        {
        }
        public DbSet<Product> product { get; set; }

    }
}
