using E_commerce.DataModel;
using E_commerce.Service;
using Microsoft.AspNetCore.Mvc;


namespace E_commerce.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserPaymentController : ControllerBase
    {
        private readonly UserPaymentService _userPaymentService;

        public UserPaymentController(UserPaymentService userPaymentService)
        {
            _userPaymentService = userPaymentService;
        }

        [HttpGet]
        public async Task<ActionResult<List<UserPayment>>> GetAllUserPayments()
        {
            var userPayments = await _userPaymentService.GetAllUserPaymentsAsync();
            return Ok(userPayments);
        }

        [HttpGet("{paymentId}")]
        public async Task<ActionResult<UserPayment>> GetUserPaymentById( int paymentId)
        {
            var userPayment = await _userPaymentService.GetUserPaymentByIdAsync(paymentId);
            if (userPayment == null)
            {
                return NotFound();
            }
            return Ok(userPayment);
        }
        [HttpPost("public/{userId}/userpayment")]
        public async Task<IActionResult> AddUserPayment(int userId, UserPayment userPayment)
        {
            if (userPayment.UserId != userId)
            {
                return BadRequest("UserId in the payload does not match the URL parameter.");
            }

            var newPaymentId = await _userPaymentService.AddUserPaymentAsync(userId, userPayment);
            return CreatedAtAction(nameof(GetUserPaymentById), new { userId, paymentId = newPaymentId }, null);
        }

        [HttpPut("public/{userId}/userpayment/{paymentId}")]
        public async Task<IActionResult> UpdateUserPayment(int userId, int paymentId, UserPayment userPayment)
        {
            if (paymentId != userPayment.Id)
            {
                return BadRequest();
            }

            await _userPaymentService.UpdateUserPaymentAsync(userId, userPayment);

            return NoContent();
        }
    }
}
