using E_commerce.DataModel;
using E_commerce.Repositories;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace E_commerce.Service
{
    public class UserInfoService
    {
        private readonly UserInfoRepository _userInfoRepository;

        public UserInfoService(UserInfoRepository userInfoRepository)
        {
            _userInfoRepository = userInfoRepository;
        }

        public async Task<List<UserInfo>> GetAllUserInfosAsync()
        {
            return await _userInfoRepository.GetAllUserInfosAsync();
        }

        public async Task<UserInfo> GetUserInfoByIdAsync(int userInfoId)
        {
            return await _userInfoRepository.GetUserInfoByIdAsync(userInfoId);
        }

        public async Task<int> AddUserInfoAsync(int userId, UserInfo userInfo)
        {
            userInfo.UserId = userId;
            return await _userInfoRepository.AddUserInfoAsync(userInfo);
        }

        public async Task UpdateUserInfoAsync(int userId, UserInfo userInfo)
        {
            if (userId != userInfo.UserId)
            {
                // Handle user mismatch if needed
                // For example, throw an exception or return a specific response
            }

            await _userInfoRepository.UpdateUserInfoAsync(userInfo);
        }
    }
}
