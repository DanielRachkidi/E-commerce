using E_commerce.Models;

namespace E_commerce.Interfaces
{
    public interface IProductRepository
    {
        IEnumerable<Product> FindAll();
        Product FindById(int id);
        Product Insert(Product product);
        Product Update(Product product);
        void DeleteById(int id);
        bool IsDatabaseConnected();
    }
}