using E_commerce.DataModel;

namespace E_commerce.Interfaces
{
    public interface IUserRepository
    {
        User FindById(int id);
        User FindByUsername(string username);
        List<User> GetAllUsers();
        User AddUser(User user);
        User UpdateUser(User user);
        void DeleteUser(int id);
    }
}
