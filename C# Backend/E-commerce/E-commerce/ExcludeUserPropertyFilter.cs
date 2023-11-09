using Microsoft.OpenApi.Models;
using Swashbuckle.AspNetCore.SwaggerGen;

public class ExcludeUserPropertyFilter : ISchemaFilter
{
    public void Apply(OpenApiSchema schema, SchemaFilterContext context)
    {
        if (context.Type == typeof(E_commerce.DataModel.UserPayment))
        {
            schema.Properties.Remove("user"); // Remove the "user" property
        }
    }
}
