using Student.Model;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;

namespace Student.Data
{
    public class ProductRepository : IProductRepository
    {
        private readonly YourDbContext _dbContext;

        public ProductRepository(YourDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public List<Product> FindByGender(string gender)
        {
            return _dbContext.product.Where(p => p.Gender == gender).ToList();
        }

        public Product FindById(int id)
        {
            return _dbContext.product.Find(id);
        }

        public List<Product> FindAll()
        {
            return _dbContext.product.ToList();
        }

        public Product Insert(Product product)
        {
            _dbContext.product.Add(product);
            _dbContext.SaveChanges();
            return product;
        }

        public Product Update(Product product)
        {
            _dbContext.product.Update(product);
            _dbContext.SaveChanges();
            return product;
        }


        public void DeleteById(int id)
        {
            var product = _dbContext.product.Find(id);
            if (product != null)
            {
                _dbContext.product.Remove(product);
                _dbContext.SaveChanges();
            }
        }
    }
}