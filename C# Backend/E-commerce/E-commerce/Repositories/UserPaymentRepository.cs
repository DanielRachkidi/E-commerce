using E_commerce.Data;
using E_commerce.DataModel;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace E_commerce.Repositories
{
    public class UserPaymentRepository
    {
        private readonly YourDbContext _dbContext;

        public UserPaymentRepository(YourDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<List<UserPayment>> GetAllUserPaymentsAsync()
        {
            return await _dbContext.UserPayments.ToListAsync();
        }

        public async Task<UserPayment> GetUserPaymentByIdAsync(int id)
        {
            return await _dbContext.UserPayments.FindAsync(id);
        }

        public async Task<int> AddUserPaymentAsync(UserPayment userPayment)
        {
            var userExists = await _dbContext.Users.AnyAsync(u => u.Id == userPayment.UserId);
            if (!userExists)
            {
                // Handle the case where the user doesn't exist
                // Return an appropriate response or throw an exception
                throw new Exception("User not found.");
            }

            _dbContext.UserPayments.Add(userPayment);
            await _dbContext.SaveChangesAsync();
            return userPayment.Id;
        }

        public async Task UpdateUserPaymentAsync(UserPayment userPayment)
        {
            _dbContext.Entry(userPayment).State = EntityState.Modified;
            await _dbContext.SaveChangesAsync();
        }



    }
}
