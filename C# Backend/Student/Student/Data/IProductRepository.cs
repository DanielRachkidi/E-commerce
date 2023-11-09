using Student.Model;

namespace Student.Data
{
    public interface IProductRepository
    {
        List<Product> FindByGender(string gender);
        Product FindById(int id);
        List<Product> FindAll();
        Product Insert(Product product);
        Product Update(Product product);
        void DeleteById(int id);
    }
}