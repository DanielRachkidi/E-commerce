using Microsoft.AspNetCore.Mvc;
using E_commerce.Interfaces;
using E_commerce.Models;
using E_commerce.DataModel;

namespace E_commerce.Controllers
{
    [ApiController]
    [Route("api/users")]
    public class UserController : ControllerBase
    {
        private readonly IUserRepository _userRepository;

        public UserController(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        [HttpGet]
        public IActionResult GetAllUsers()
        {
            var users = _userRepository.GetAllUsers();
            return Ok(users);
        }

        [HttpGet("{id}")]
        public IActionResult GetUserById(int id)
        {
            var user = _userRepository.FindById(id);
            if (user == null)
            {
                return NotFound();
            }
            return Ok(user);
        }

        [HttpPost]
        public IActionResult AddUser(User user)
        {
            var newUser = _userRepository.AddUser(user);
            return CreatedAtAction(nameof(GetUserById), new { id = newUser.Id }, new
            {
                newUser.Username,
                newUser.Password
            });
        }


        [HttpPut("{id}")]
        public IActionResult UpdateUser(int id, User updatedUser)
        {
            var existingUser = _userRepository.FindById(id);
            if (existingUser == null)
            {
                return NotFound();
            }
            updatedUser.Id = id;
            var updatedUserResult = _userRepository.UpdateUser(updatedUser);
            return Ok(updatedUserResult);
        }

        [HttpDelete("{id}")]
        public IActionResult DeleteUser(int id)
        {
            var existingUser = _userRepository.FindById(id);
            if (existingUser == null)
            {
                return NotFound();
            }
            _userRepository.DeleteUser(id);
            return NoContent();
        }
    }
}
