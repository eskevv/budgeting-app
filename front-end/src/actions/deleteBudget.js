// rrd import
import {redirect} from "react-router-dom";

// library
import {toast} from "react-toastify";

export async function deleteBudget({params}) {
  try {
    const response = await fetch(`http://localhost:8080/api/expenses`)
    const responseData = await response.json()
    const forBudget = responseData.filter(x => +x.budget.id === +params.id)
    // TODO: too many server requests... add endpoint for this action
    for (let expense of forBudget) {
      await fetch(`http://localhost:8080/api/expenses/${expense.id}`, {
        method: "DELETE"
      })
    }
  } catch (e) {
    throw new Error("There was a problem deleting your budget.");
  }
  try {
    const response = await fetch(`http://localhost:8080/api/budgets/${params.id}`, {
      method: 'DELETE'
    });
    toast.success("Budget deleted successfully!");
  } catch (e) {
    throw new Error("There was a problem deleting your budget.");
  }
  return redirect("/");
}
