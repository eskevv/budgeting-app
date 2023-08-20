// rrd imports
import {redirect} from "react-router-dom";

// library
import {toast} from "react-toastify";

// helpers
import {deleteItem} from "../helpers";

export async function logoutAction() {
  // delete the user
  deleteItem({
    key: "userName"
  })
  deleteItem({
    key: "budgets"
  })
  deleteItem({
    key: "expenses"
  })

  try {
    const response = await fetch('http://localhost:8080/api/budgets', {
      method: 'DELETE'
    });
    if ( response.ok ) {
      console.log('All budgets deleted successfully.');
    } else {
      console.error('Error deleting budgets.');
    }
  } catch (error) {
    console.error('Error:', error);
  }
  toast.success("You’ve deleted your account!")
  // return redirect
  return redirect("/")
}