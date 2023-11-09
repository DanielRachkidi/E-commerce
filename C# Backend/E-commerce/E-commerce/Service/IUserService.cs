using E_commerce.DataModel;

namespace E_commerce.Service
{
    public interface IUserService
    {
        User Login(string username, string password);
        List<User> FindByCredentials(string username, string password);
        Task<User> FindByCredentialsAsync(string username, string password);

        Task<User> GetUserById(int id);

      
    }
}
