using E_commerce.DataModel;
using E_commerce.Models;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;

namespace E_commerce.Data
{
    public class YourDbContext : DbContext
    {

        public YourDbContext(DbContextOptions<YourDbContext> options) : base(options)
        {
        }
        public DbSet<Product> product { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<UserPayment> UserPayments { get; set; }

        public DbSet<UserInfo> UserInfos { get; set; }




    }
}
