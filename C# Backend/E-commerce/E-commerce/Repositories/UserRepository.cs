using E_commerce.Data;
using E_commerce.DataModel;
using E_commerce.Interfaces;
using System.Text;
using System.Security.Cryptography;

namespace E_commerce.Repositories
{
    public class UserRepository : IUserRepository
    {
        private readonly YourDbContext _dbContext;

        public UserRepository(YourDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public User FindById(int id)
        {
            return _dbContext.Users.Find(id);
        }

        public User FindByUsername(string username)
        {
            return _dbContext.Users.FirstOrDefault(u => u.Username == username);
        }

        public List<User> GetAllUsers()
        {
            return _dbContext.Users.ToList();
        }

        public User AddUser(User user)
        {
            string hashedPassword = HashPassword(user.Password);
            user.Password = hashedPassword;

            _dbContext.Users.Add(user);
            _dbContext.SaveChanges();
            return user;
        }

        private string HashPassword(string password)
        {
            // Implement your password hashing logic here
            // For example, you can use a cryptographic algorithm like SHA256

            // Example using SHA256 hashing algorithm
            using (var sha256 = SHA256.Create())
            {
                byte[] hashedBytes = sha256.ComputeHash(Encoding.UTF8.GetBytes(password));
                return Convert.ToBase64String(hashedBytes);
            }
        }

            public User UpdateUser(User user)
        {
            _dbContext.Users.Update(user);
            _dbContext.SaveChanges();
            return user;
        }

        public void DeleteUser(int id)
        {
            var user = _dbContext.Users.Find(id);
            if (user != null)
            {
                _dbContext.Users.Remove(user);
                _dbContext.SaveChanges();
            }
        }
    }
}