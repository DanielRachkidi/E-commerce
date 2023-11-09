using Microsoft.AspNetCore.Mvc;
using E_commerce.Interfaces;
using E_commerce.Models;

namespace E_commerce.Controllers
{
    [ApiController]
    [Route("api/products")]
    public class ProductController : ControllerBase
    {
        private readonly IProductRepository _productRepository;

        public ProductController(IProductRepository productRepository)
        {
            _productRepository = productRepository;
        }

        [HttpGet]
        public IActionResult GetAllProducts()
        {
            var products = _productRepository.FindAll();
            return Ok(products);
        }

        [HttpGet("{id}")]
        public IActionResult GetProductById(int id)
        {
            var product = _productRepository.FindById(id);
            if (product == null)
            {
                return NotFound();
            }
            return Ok(product);
        }

        [HttpPost]
        public IActionResult AddProduct(Product product)
        {
            var newProduct = _productRepository.Insert(product);
            return CreatedAtAction(nameof(GetProductById), new { id = newProduct.Id }, newProduct);
        }

        [HttpPut("{id}")]
        public IActionResult UpdateProduct(int id, Product updatedProduct)
        {
            var existingProduct = _productRepository.FindById(id);
            if (existingProduct == null)
            {
                return NotFound();
            }
            updatedProduct.Id = id;
            var updatedProductResult = _productRepository.Update(updatedProduct);
            return Ok(updatedProductResult);
        }

        [HttpDelete("{id}")]
        public IActionResult DeleteProduct(int id)
        {
            var existingProduct = _productRepository.FindById(id);
            if (existingProduct == null)
            {
                return NotFound();
            }
            _productRepository.DeleteById(id);
            return NoContent();
        }

        [HttpGet("connection")]
        public IActionResult CheckDatabaseConnection()
        {
            bool isConnected = _productRepository.IsDatabaseConnected();
            if (isConnected)
            {
                return Ok(true);
            }
            else
            {
                return NotFound(false);
            }
        }
    }
}