// StudentContext.cs
using Microsoft.EntityFrameworkCore;
using Student.Model;

namespace Student.Data
{
    public class StudentContext : DbContext
    {
        public StudentContext(DbContextOptions<StudentContext> options) : base(options)
        {
        }

        public DbSet<Study> study { get; set; }
    }
}
