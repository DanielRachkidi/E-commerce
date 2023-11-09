using System.Collections.Generic;
using System.Threading.Tasks;
using E_commerce.Data;
using E_commerce.DataModel;
using E_commerce.Repositories;
using Microsoft.EntityFrameworkCore;

namespace E_commerce.Service
{
    public class YourUserServiceImplementation : IUserService
    {
        private readonly YourDbContext _dbContext;
      

        public YourUserServiceImplementation(YourDbContext dbContext)
        {
            _dbContext = dbContext;
        }





        public async Task<User> GetUserById(int id)
        {
            return await _dbContext.Users.FindAsync(id);
        }

        public async Task<IEnumerable<User>> GetAllUsers()
        {
            return await _dbContext.Users.ToListAsync();
        }

        public async Task<User> FindByCredentialsAsync(string username, string password)
        {
            return await _dbContext.Users
                .FirstOrDefaultAsync(u => u.Username == username && u.Password == password);
        }

        public User Login(string username, string password)
        {
            throw new NotImplementedException();
        }

        public List<User> FindByCredentials(string username, string password)
        {
            throw new NotImplementedException();
        }



    }
}
