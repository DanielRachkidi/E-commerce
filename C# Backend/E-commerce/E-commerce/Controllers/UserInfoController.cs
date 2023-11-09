using E_commerce.DataModel;
using E_commerce.Service;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace E_commerce.Controllers
{
    [Route("api/UserInfo")]
    [ApiController]
    public class UserInfoController : ControllerBase
    {
        private readonly UserInfoService _userInfoService;

        public UserInfoController(UserInfoService userInfoService)
        {
            _userInfoService = userInfoService;
        }

        [HttpGet]
        public async Task<ActionResult<List<UserInfo>>> GetAllUserInfos()
        {
            var userInfos = await _userInfoService.GetAllUserInfosAsync();
            return Ok(userInfos);
        }

        [HttpGet("{userInfoId}")]
        public async Task<ActionResult<UserInfo>> GetUserInfoById(int userInfoId)
        {
            var userInfo = await _userInfoService.GetUserInfoByIdAsync(userInfoId);
            if (userInfo == null)
            {
                return NotFound();
            }
            return Ok(userInfo);
        }

        [HttpPost("public/{userId}/userinfo")]
        public async Task<ActionResult<int>> AddUserInfo(int userId, UserInfo userInfo)
        {
            var newUserInfoId = await _userInfoService.AddUserInfoAsync(userId, userInfo);
            return CreatedAtAction(nameof(GetUserInfoById), new { userInfoId = newUserInfoId }, newUserInfoId);
        }

        [HttpPut("public/{userId}/userinfo/{infoId}")] // in the request body I can change the  userId and still work  do the exception the user ID doesnt change 
        public async Task<IActionResult> UpdateUserInfo(int userId, int infoId, UserInfo userInfo)
        {
            if (infoId != userInfo.Id)
            {
                return BadRequest();
            }

            await _userInfoService.UpdateUserInfoAsync(userId, userInfo);

            return NoContent();
        }
    }
}
