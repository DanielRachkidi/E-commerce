using E_commerce.Data;
using E_commerce.DataModel;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace E_commerce.Repositories
{
    public class UserInfoRepository
    {
        private readonly YourDbContext _dbContext;

        public UserInfoRepository(YourDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<List<UserInfo>> GetAllUserInfosAsync()
        {
            return await _dbContext.UserInfos.ToListAsync();
        }

        public async Task<UserInfo> GetUserInfoByIdAsync(int userInfoId)
        {
            return await _dbContext.UserInfos.FindAsync(userInfoId);
        }

        public async Task<int> AddUserInfoAsync(UserInfo userInfo)
        {
            _dbContext.UserInfos.Add(userInfo);
            await _dbContext.SaveChangesAsync();
            return userInfo.Id;
        }

        public async Task UpdateUserInfoAsync(UserInfo userInfo)
        {
            _dbContext.Entry(userInfo).State = EntityState.Modified;
            await _dbContext.SaveChangesAsync();
        }
    }
}
