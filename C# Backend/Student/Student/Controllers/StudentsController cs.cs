// StudentsController.cs
using Microsoft.AspNetCore.Mvc;
using Student.Data;
using Student.Model;

namespace Student.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class StudentsController : ControllerBase
    {
        private readonly StudentContext _dbContext;

        public StudentsController(StudentContext dbContext)
        {
            _dbContext = dbContext;
        }

        [HttpGet]
        public IActionResult Get()
        {
            return Ok(_dbContext.study);
        }

        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            var student = _dbContext.study.Find(id);
            if (student == null)
            {
                return NotFound();
            }
            return Ok(student);
        }

        [HttpPost]
        public IActionResult Post(Study student)
        {
            _dbContext.study.Add(student);
            _dbContext.SaveChanges();
            return CreatedAtAction(nameof(Get), new { id = student.Id }, student);
        }

        [HttpPut("{id}")]
        public IActionResult Put(int id, Study updatedStudent)
        {
            var student = _dbContext.study.Find(id);
            if (student == null)
            {
                return NotFound();
            }

            student.Name = updatedStudent.Name;
            student.Age = updatedStudent.Age;
            _dbContext.SaveChanges();
            return NoContent();
        }

        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            var student = _dbContext.study.Find(id);
            if (student == null)
            {
                return NotFound();
            }

            _dbContext.study.Remove(student);
            _dbContext.SaveChanges();
            return NoContent();
        }


        [HttpGet("testconnection")]
        public IActionResult TestConnection()
        {
            try
            {
                var records = _dbContext.study.ToList();
                return Ok(records);
            }
            catch (Exception ex)
            {
                return StatusCode(500, "Error connecting to the database.");
            }
        }


    }
}
